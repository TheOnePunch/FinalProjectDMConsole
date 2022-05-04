package com.example.dmconsole

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dmconsole.databinding.ActivityDetailedNpcBinding

val hairArr = arrayOf(" Brown ", " Black ", " Blonde ", " Red ")
val hairStyleArr = arrayOf(" Short ", " Long ", " Messy ", " Neat")
val eyesAdjArr = arrayOf(  " Piercing ", " Gentle ", " Lax ", " Beady ", " Bloodshot ", " Small ", " Sunken ", " Big ")
val eyeColorArr = arrayOf(  " Dark Green ", " Light Green ", " Green ", " Dark Blue ", " Light Blue ", " Blue ", " Dark Brown ", " Light Brown ", " Brown ", " Hazel ")
val voiceArr = arrayOf(  " Deep ", " Full ", " Imposing ", " Powerful ", " Warm ", " Sweet ", " Smooth ", " Bright ")
val skinArr = arrayOf(" Pale ", " Fair ", " Dark ", " Tan ")
val buildArr = arrayOf(" Lean ", " Rotund ", " Athletic ", " Average ")
val jobArr = arrayOf("Blacksmith", "Baker", "Butcher","Tailor", "Leather Worker", "Shoemaker", "Farmer","Architect", "Stone carver",
        "Candle maker", "Carpenter", "Cartographer", "Physician", "Locksmith", "Jeweler", "Brewer", "Bricklayer", "Moneylender",
        "Miner", "Fisherman", "Forester", "Scribe", "Sailor", "Hunter", "Innkeeper","Herbalist","Gravedigger","Potter",
        "Rat catcher","Weaver")

class DetailedNPC : AppCompatActivity() {
    private lateinit var binding: ActivityDetailedNpcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedNpcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()

        val npcName = intent.getSerializableExtra("Name").toString()

        binding.npcName.setText(npcName)

        generateDetails()
    }

    private fun generateDetails(){
        binding.hairDisplay.setText(hairStyleArr[randomNumber(hairStyleArr.size - 1)] + " " + hairArr[randomNumber(
            hairArr.size - 1)])
        binding.eyesDisplay.setText(
            eyesAdjArr[randomNumber(eyesAdjArr.size - 1)] + " " + eyeColorArr[randomNumber(
            eyeColorArr.size - 1)])
        binding.voiceDisplay.setText(
            voiceArr[randomNumber(voiceArr.size - 1)] )
        binding.skinDisplay.setText(skinArr[randomNumber(skinArr.size - 1)])
        binding.buildDisplay.setText(buildArr[randomNumber(buildArr.size - 1)])
        binding.jobDisplay.setText(jobArr[randomNumber(jobArr.size - 1)])

    }

    private fun randomNumber(max : Int) : Int{
        val result = (0..max).random()
        return result
    }
}