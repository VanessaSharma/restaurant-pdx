import org.sql2o.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class Cuisine {
  private String type;
  private int id;



  public Cuisine(String type) {
    this.type = type;
    }

  public String getName() {
    return type;
  }

  public int getId() {
    return id;
  }

  public static Cuisine find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM cuisines WHERE id=:id";
      Cuisine cuisine = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Cuisine.class);
    return cuisine;
    }
  }

  public static List<Cuisine> all() {
    String sql = "SELECT id, type FROM cuisines";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Cuisine.class);
    }
  }
 public void save() {
   try(Connection con = DB.sql2o.open()){
     String sql = "INSERT INTO cuisines(type) VALUES (:type)";
     this.id = (int) con.createQuery(sql, true)
     .addParameter("type", this.type)
     .executeUpdate()
     .getKey();
   }
 }
  @Override
  public boolean equals(Object otherCuisine) {
    if (!(otherCuisine instanceof Cuisine)) {
      return false;
    } else {
      Cuisine newCuisine = (Cuisine) otherCuisine;
      return this.type.equals(newCuisine.type);
    }
  }
}
