package com.abdi.myquran

import com.google.gson.annotations.SerializedName

data class DetailSurahResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class SuratSebelumnya(
	@field:SerializedName("jumlahAyat")
	val jumlahAyat: Int,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("nomor")
	val nomor: Int,

	@field:SerializedName("namaLatin")
	val namaLatin: String
)

data class SuratSelanjutnya(

	@field:SerializedName("jumlahAyat")
	val jumlahAyat: Int,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("nomor")
	val nomor: Int,

	@field:SerializedName("namaLatin")
	val namaLatin: String
)

data class Data(

	@field:SerializedName("jumlahAyat")
	val jumlahAyat: Int,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("audioFull")
	val audioFull: AudioFull,

	@field:SerializedName("suratSebelumnya")
	val suratSebelumnya: SuratSebelumnya?,

	@field:SerializedName("tempatTurun")
	val tempatTurun: String,

	@field:SerializedName("ayat")
	val ayat: List<AyatItem>,

	@field:SerializedName("arti")
	val arti: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("suratSelanjutnya")
	val suratSelanjutnya: SuratSelanjutnya,

	@field:SerializedName("nomor")
	val nomor: Int,

	@field:SerializedName("namaLatin")
	val namaLatin: String
)

data class Audio(

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

data class AyatItem(

	@field:SerializedName("teksArab")
	val teksArab: String,

	@field:SerializedName("teksLatin")
	val teksLatin: String,

	@field:SerializedName("nomorAyat")
	val nomorAyat: Int,

	@field:SerializedName("audio")
	val audio: Audio,

	@field:SerializedName("teksIndonesia")
	val teksIndonesia: String
)
