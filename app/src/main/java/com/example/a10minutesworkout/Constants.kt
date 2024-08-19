package com.example.a10minutesworkout

object Constants {

    fun defaultExerciseList() : ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(
            1, "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false, false
        )
        exerciseList.add(jumpingJacks)

        val highKneesRunning = ExerciseModel(
            2, "High Knees Running",
            R.drawable.ic_high_knees_running,
            false, false
        )
        exerciseList.add(highKneesRunning)

        val squat = ExerciseModel(
            3, "Squat",
            R.drawable.ic_squat,
            false, false
        )
        exerciseList.add(squat)

        val lunge = ExerciseModel(
            4, "Lunge",
            R.drawable.ic_lunge,
            false, false
        )
        exerciseList.add(lunge)

        val setUpOntoChair = ExerciseModel(
            5, "Set up onto chair",
            R.drawable.ic_set_up_onto_chair,
            false, false
        )
        exerciseList.add(setUpOntoChair)

        val wallSit = ExerciseModel(
            6, "Wall sit",
            R.drawable.ic_wall_sit,
            false, false
        )
        exerciseList.add(wallSit)

        val plank = ExerciseModel(
            7, "Plank",
            R.drawable.ic_plank,
            false, false
        )
        exerciseList.add(plank)

        val sidePlank = ExerciseModel(
            8, "Side plank",
            R.drawable.ic_side_plank,
            false, false
        )
        exerciseList.add(sidePlank)

        val abdominalCrunch = ExerciseModel(
            9, "Abdominal Crunch",
            R.drawable.ic_abdominal_crunch,
            false, false
        )
        exerciseList.add(abdominalCrunch)

        val pushUps = ExerciseModel(
            10, "Push ups",
            R.drawable.ic_push_ups,
            false, false
        )
        exerciseList.add(pushUps)

        val pushUpsRotation = ExerciseModel(
            11, "Push ups rotation",
            R.drawable.ic_push_ups_rotation,
            false, false
        )
        exerciseList.add(pushUpsRotation)

        val tricepsDipOnChair = ExerciseModel(
            12, "Triceps dip on chair",
            R.drawable.ic_triceps_dip_on_chair,
            false, false
        )
        exerciseList.add(tricepsDipOnChair)

        return exerciseList
    }
}




















