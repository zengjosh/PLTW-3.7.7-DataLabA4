import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import core.data.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

public class PokemonMain {
  static int poisontype = 0;
  static int ghosttype = 0;
  static int darktype = 0;
  static int bugtype = 0;
  static int rocktype = 0;
  static int dragontype = 0;
  static int flyingtype = 0;
  static int steeltype = 0;
  static int fightingtype = 0;

  public static void addType(String type) {
    if (type.equals("poison")) {
      poisontype++;
    } else if (type.equals("ghost")) {
      ghosttype++;
    } else if (type.equals("dark")) {
      darktype++;
    } else if (type.equals("bug")) {
      bugtype++;
    } else if (type.equals("rock")) {
      rocktype++;
    } else if (type.equals("dragon")) {
      dragontype++;
    } else if (type.equals("flying")) {
      flyingtype++;
    } else if (type.equals("steel")) {
      steeltype++;
    } else if (type.equals("fighting")) {
      fightingtype++;
    }
  }

  private static void createChart(List<String> pokemonTypes, List<Integer> typeCounts) {
    CategoryChart chart = new CategoryChartBuilder().width(800).height(600)
            .title("Pokémon Type Distribution")
            .xAxisTitle("Types")
            .yAxisTitle("Count")
            .build();

    chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
    chart.addSeries("Pokémon Types", pokemonTypes, typeCounts);
    new SwingWrapper<>(chart).displayChart();
  }
  
   public static void main(String[] args) {
     DataSource ds = DataSource.connect("pokemon.csv").load();

     ArrayList<String> type1 = ds.fetchStringList("type1");
     ArrayList<String> type2 = ds.fetchStringList("type2");

      for (int i = 0; i < type1.size(); i++) {
        addType(type1.get(i));
      }
  
      for (int i = 0; i < type2.size(); i++) {
        addType(type2.get(i));
      }

     System.out.println(poisontype);
     
     List<String> pokemonTypes = Arrays.asList("Poison", "Ghost", "Dark", "Bug", "Rock", "Dragon", "Flying", "Steel", "Fighting");
     List<Integer> typeCounts = Arrays.asList(poisontype, ghosttype, darktype, bugtype, rocktype, dragontype, flyingtype, steeltype, fightingtype);
      createChart(pokemonTypes, typeCounts);
  }
}