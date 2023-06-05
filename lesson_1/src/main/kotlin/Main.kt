import kotlin.math.log

fun main() {


    // --------------- list -------------------------
    val listName = mutableListOf<String>();

    // Them phan tu
    listName.add("vinh")
    listName.add("Trang")
    listName.add("Han")

    // xoa phan tu
    listName.removeAt(0)

    // cap nhat phan tu
    listName[0] = "Long"

    // ---------------- Set --------------------------
    // lưu phần tử không trùng lặp
    val setNumber = mutableSetOf<Int>()

    // them phan tu
    setNumber.add(1)
    setNumber.add(2)
    setNumber.add(3)


}


