import org.sql2o.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Restaurant {
  private String name;
  private int rating;
  private int price;
  private int id;
  private int cuisineId;

  public Restaurant(String name, int rating, int price, int cuisineId) {
    this.name = name;
    this.rating = rating;
    this.price = price;
    this.cuisineId = cuisineId;
  }

  public String getName() {
    return name;
  }

  public int getRating() {
    return rating;
  }

  public int getPrice() {
    return price;
  }

  public int getId() {
    return id;
  }

  public static Restaurant find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants WHERE id = :id";
      Restaurant  restaurant= con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Restaurant.class);
      return restaurant;
    }
  }

  public static List<Restaurant> all() {
    String sql = "SELECT id, name, rating, price FROM restaurants";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Restaurant.class);
    }
  }
  public int getCuisineId() {
    return cuisineId;
  }

  @Override
  public boolean equals(Object otherRestaurant) {
    if (!(otherRestaurant instanceof Restaurant)) {
      return false;
    } else {
      Restaurant newRestaurant = (Restaurant) otherRestaurant;
      return this.getName().equals(newRestaurant.getName()) && this.getId() == newRestaurant.getId() && this.getCuisineId() == newRestaurant.getCuisineId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO restaurants (name, rating, price) VALUES (:name, :rating, :price)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("rating", this.rating)
        .addParameter("price", this.price)
        .executeUpdate()
        .getKey();
    }
  }

  public void update(String name) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "UPDATE restaurants SET name, rating, price = :name,:rating,:price  WHERE id = :id";
     con.createQuery(sql)
       .addParameter("name", name)
       .addParameter("id", id)
       .executeUpdate();
     }
   }

  public void delete() {
  try(Connection con = DB.sql2o.open()) {
  String sql = "DELETE FROM restaurants WHERE id = :id;";
  con.createQuery(sql)
    .addParameter("id", id)
    .executeUpdate();
    }
  }
}
