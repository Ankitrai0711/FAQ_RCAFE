package com.example.assessmentone


 class TrackActivity {
        val hashMap = mutableMapOf<String, Long>()

        fun progress(id: String, time: Long): Boolean {
            val currentTime = hashMap[id]

            if (currentTime == null || time - currentTime >= 5000) {
                hashMap[id] = time
                return true
            } else {
                return false
            }
        }
    }

    fun main() {
        val t = TrackActivity()

        val currentTime = System.currentTimeMillis()
        println(t.progress("task1", currentTime))

        println(t.progress("task1", currentTime + 2000))

        println(t.progress("task1", currentTime + 3000))

        println(t.progress("task2", currentTime + 6000))

    }



