package io.github.kimkr.mvvmsample.persistence.model

enum class Gender(val code: String) {
    FEMALE("여"),
    MALE("남"),;

    companion object {
        fun from(code: String) : Gender? {
            for(gender in Gender.values()){
                if(gender.code == code){
                    return gender
                }
            }
            return null
        }
    }
}