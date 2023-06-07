fun main(args: Array<String>) {
//    numberFilter(object : (Int) -> Boolean {
//        override fun invoke(p1: Int): Boolean {
//            return p1 % 3 == 1
//        }
//    })

    val cat = Animal()
    cat.name = "kitty"
    cat.weight = 10f

    println(cat.weight)

}

//fun numberFilter(callBack: (Int) -> Boolean) {
//    for (element in 1..100) {
//        val isValidCondition = callBack(element)
//        if (isValidCondition) {
//            println(element)
//        }
//    }
//}