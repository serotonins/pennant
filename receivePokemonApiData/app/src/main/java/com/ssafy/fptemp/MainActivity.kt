package com.ssafy.fptemp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.ssafy.fptemp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var nameList = mutableListOf<String>()
    var pokeDataList = mutableListOf<PokeData>()

    var pokeDataListToFireStore = mutableListOf<Any>()
    lateinit var nameRes : NamesResponse

    var firestoreList = listOf<Any>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tv.text = "what"

        val pokeService = ApplicationClass.baseRetrofit.create(PokeService::class.java)

        val db = Firebase.firestore


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = pokeService.getNames(151)

                if (result.isSuccessful) {
                    nameRes = result.body() as NamesResponse
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.tv.text = nameRes.toString()
                        Log.d(TAG, "onCreate: ${nameRes.results}")
                    }
                }

                db.collection("pokemon")
                    .get()
                    .addOnSuccessListener {result ->
                        for (document in result) {

                        }
                    }

                for (i in 0 until 151) {
                    var name = nameRes.results[i].name
                    nameList.add(name)
                    var speciesResult = pokeService.getMythicalLegendary(name).body() as SpeciesResponse
                    var imageTypeResult = pokeService.getImageType(name).body() as ImageTypeResponse
//                    Log.d(TAG, "onCreate: ${speciesResult.body()}")
//                    Log.d(TAG, "onCreate: ${imageTypeResult.body()}")
                    var typeToString = mutableListOf<String>()
                    for (j in 0 until imageTypeResult.types.size) {
                        typeToString.add(imageTypeResult.types[j].type.typeName)
                    }
                    var koreanName = ""
                    for (j in 0 until speciesResult.names.size) {
                        if (speciesResult.names[j].language.nationName == "ko") {
                            koreanName = speciesResult.names[j].name
                            break
                        }
                    }

                    withContext(Dispatchers.Main) {
                        binding.tv.text = koreanName
                    }

                    pokeDataList.add(PokeData(
                        id = i,
                        name = name,
                        nameKorean = koreanName,
                        imageOfficial = imageTypeResult.sprites.other.officialArtwork.front_default,
                        image3D = imageTypeResult.sprites.other.home.front_default,
                        isLegendary = speciesResult.is_legendary,
                        isMythical = speciesResult.is_mythical,
                        percentage = 100.0,
                        type = typeToString.toList()
                    ))

                    val pokemonToFirebase = hashMapOf(
                        "id" to i,
                        "name" to name,
                        "nameKorean" to koreanName,
                        "imageOfficial" to imageTypeResult.sprites.other.officialArtwork.front_default,
                        "image3D" to imageTypeResult.sprites.other.home.front_default,
                        "isLegendary" to speciesResult.is_legendary,
                        "isMythical" to speciesResult.is_mythical,
                        "percentage" to 100.0,
                        "type" to typeToString.toList()
                    )


                    // 수정할 때는 document ID(랜덤으로 생성되는 그것)을 알아내고
                    var documentId = ""
                    db.collection("pokemon")
                        .whereEqualTo("name", name)
                        .get()
                        .addOnSuccessListener { result ->
                            
                            for (document in result) {
                                documentId = document.id
                                Log.d(TAG, "onCreate: !!!!!!!!!!!!!!!!!!!!!! ${documentId.toString()} !!!!!!!!!!!!!!!!!!")
                            }
                        }.await()

//                    Log.d(TAG, "onCreate: ${documentId.toString()} !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")

                    // document ID로 찾아가서 수정
                    db.collection("pokemon").document(documentId)
                        .update(
                            mutableMapOf(
                                "nameKorean" to koreanName,
                                "imageOfficial" to imageTypeResult.sprites.other.officialArtwork.front_default,
                                "image3D" to imageTypeResult.sprites.other.home.front_default,
                            ) as Map<String, Any>
                        )
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }


//                    // 이거는 데이터 추가할 때용 코드
//                    db.collection("pokemon")
//                        .add(pokemonToFirebase)
//                        .addOnSuccessListener { documentReference ->
//                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//                        }
//                        .addOnFailureListener { e ->
//                            Log.w(TAG, "Error adding document", e)
//                        }
                }



            } catch (e : Exception) {
                withContext(Dispatchers.Main) {
                    Log.d(TAG, "onCreate: 통신 에러..........")
                    e.printStackTrace()
                }
            }

        }

//        Log.d(TAG, "onCreate: ${pokeDataList.size} !!!!!!!!!!!!!!!!!!!!!")

    }
}