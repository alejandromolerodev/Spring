import android.os.Parcelable
import com.example.foodhub.modelo.Categoria
import com.example.foodhub.modelo.NutriScore
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Parcelize
data class Producto(
    val id: Long? = null,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("empNombre")
    val marca: String,
    @SerializedName("nutriScore")
    val nutriScore: NutriScore?,
    @SerializedName("peso")
    val peso: String,
    @SerializedName("categoria")
    val categoria: Categoria?,
    @SerializedName("url_image")
    val imagenUrl: String?,
    @SerializedName("codigoBarras")
    val codigoBarras: String,
    @SerializedName("fechaCad")
    var fechaCad: Date?
) : Parcelable {

    companion object {
        val EMPTY = Producto(
            nombre = "",
            marca = "",
            nutriScore = null,
            peso = "",
            categoria = null,
            imagenUrl = null,
            codigoBarras = "",
            fechaCad = null
        )

        fun fromJson(json: JSONObject, barcode: String): Producto {
            return Producto(
                id = json.optLong("id").takeIf { it != 0L },
                nombre = json.optString("nombre", "Producto Desconocido"),
                marca = json.optString("empNombre", "Marca no disponible"),
                peso = json.optString("peso", "Peso no especificado"),
                categoria = json.optJSONObject("categoria")?.let {
                    Categoria(
                        nombre = it.optString("nombre", "Sin categoría")
                    )
                },
                imagenUrl = json.optString("url_image").takeIf { it.isNotEmpty() },
                codigoBarras = json.optString("codigoBarras", barcode),
                nutriScore = NutriScore.fromString(json.optString("nutriScore")),
                fechaCad = parseDate(json.optString("fechaCad"))
            )
        }

        private fun parseDate(dateString: String): Date? {
            return try {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString)
            } catch (e: Exception) {
                null
            }
        }
    }
}
