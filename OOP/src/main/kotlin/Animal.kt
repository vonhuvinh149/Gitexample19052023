class Animal(name: String, age: Float) {
    private var name: String = ""
    private var age: Float = 0f

    init {
        this.name = name
        this.age = age
    }

    fun getName() {
        println(this.name)
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getAge() {
        println(this.age)
    }

    fun setAge(age: Float) {
        this.age = age
    }

}