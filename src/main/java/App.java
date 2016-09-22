import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

   get("/", (request, response) -> {
     Map<String, Object> model = new HashMap<String, Object>();
     model.put("template", "templates/index.vtl");
     model.put("cuisines", Cuisine.all());
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

  get("cuisines/:cuisine_id/restaurants/:id", (request, response)-> {
    Map<String, Object> model = new HashMap<String, Object>();
    Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":cuisine_id")));
    Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":id")));
    model.put("cuisine", cuisine);
    model.put("restaurant", restaurant);
    model.put("template", "templates/restaurant.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/cuisines/:cuisine_id/restaurants/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params("id")));
    String type = request.queryParams("type");
    Cuisine cuisine = Cuisine.find(restaurant.getCuisineId());
    restaurant.update(type);
    String url = String.format("/cuisines/%d/restaurants/%d", cuisine.getId(), restaurant.getId());
    response.redirect(url);
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/restaurants", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Cuisine cuisine = Cuisine.find(Integer.parseInt(request.queryParams("cuisineId")));
    String name = request.queryParams("name");
    int rating = Integer.parseInt(request.queryParams("rating"));
    int price = Integer.parseInt(request.queryParams("price"));
    Restaurant newRestaurant = new Restaurant(name, rating, price, cuisine.getId());
    newRestaurant.save();
    model.put("cuisine", cuisine);
    model.put("template", "templates/cuisine-restaurants-form-success.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/restaurants/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":id")));
    model.put("restaurant", restaurant);
    model.put("template", "templates/restaurant.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/cuisines", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    String name = request.queryParams("names");
    Cuisine newCuisine = new Cuisine(name);
    newCuisine.save();
    model.put("template", "templates/cuisine-success.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/restaurants", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("restaurants", Restaurant.all());
    model.put("template", "templates/restaurants.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/cuisines/new", (request, response) -> {
  Map<String, Object> model = new HashMap<String, Object>();
  model.put("template", "templates/cuisine-form.vtl");
  return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/cuisines", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("cuisines", Cuisine.all());
    model.put("template", "templates/cuisines.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/cuisines/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":id")));
    model.put("cuisine", cuisine);
    model.put("template", "templates/cuisine.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/cuisines/:cuisine_id/restaurants/:id/delete", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params("id")));
    Cuisine cuisine = Cuisine.find(restaurant.getCuisineId());
    restaurant.delete();
    model.put("cuisine", cuisine);
    model.put("template", "templates/cuisine.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  }
}
