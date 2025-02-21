package org.molerodev.foodhubmvc.model;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 21/2/25
 */
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Receta {

    // Nombre de la receta
    private String label;

    // URL de la receta (link al sitio web de la receta)
    private String url;

    // Imagen de la receta
    private String image;

    // Ingredientes de la receta
    private List<String> ingredients;

    // Otros posibles campos según la respuesta de la API de Edamam
    private String source;

    // Atributos para el cálculo de información nutricional (si es necesario)
    private NutritionalInfo totalNutrients;

    // Getters y setters
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public NutritionalInfo getTotalNutrients() {
        return totalNutrients;
    }

    public void setTotalNutrients(NutritionalInfo totalNutrients) {
        this.totalNutrients = totalNutrients;
    }

    // Clase para manejar la información nutricional (si la API lo devuelve)
    public static class NutritionalInfo {
        private Nutrient calories;
        private Nutrient fat;
        private Nutrient protein;
        private Nutrient carbs;

        public Nutrient getCalories() {
            return calories;
        }

        public void setCalories(Nutrient calories) {
            this.calories = calories;
        }

        public Nutrient getFat() {
            return fat;
        }

        public void setFat(Nutrient fat) {
            this.fat = fat;
        }

        public Nutrient getProtein() {
            return protein;
        }

        public void setProtein(Nutrient protein) {
            this.protein = protein;
        }

        public Nutrient getCarbs() {
            return carbs;
        }

        public void setCarbs(Nutrient carbs) {
            this.carbs = carbs;
        }

        // Clase para manejar cada nutriente
        public static class Nutrient {
            private double quantity;
            private String unit;

            public double getQuantity() {
                return quantity;
            }

            public void setQuantity(double quantity) {
                this.quantity = quantity;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }
        }
    }
}
