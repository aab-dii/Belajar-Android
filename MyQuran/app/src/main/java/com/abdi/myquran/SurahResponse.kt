package com.abdi.myquran

import com.google.gson.annotations.SerializedName

data class SurahResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

data class AudioFull(

	@field:SerializedName("01")
	val jsonMember01: String,

	@field:SerializedName("02")
	val jsonMember02: String,

	@field:SerializedName("03")
	val jsonMember03: String,

	@field:SerializedName("04")
	val jsonMember04: String,

	@field:SerializedName("05")
	val jsonMember05: String
)

data class DataItem(

	@field:SerializedName("jumlahAyat")
	val jumlahAyat: Int,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("audioFull")
	val audioFull: AudioFull,

	@field:SerializedName("tempatTurun")
	val tempatTurun: String,

	@field:SerializedName("arti")
	val arti: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("nomor")
	val nomor: Int,

	@field:SerializedName("namaLatin")
	val namaLatin: String
)
