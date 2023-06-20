import java.io.Serializable

class User(
    var id: String,
    var pw: String,
    var name: String,
    var phone: String,
    var point: String
): Serializable {
    constructor(): this("","","", "", "")

}
