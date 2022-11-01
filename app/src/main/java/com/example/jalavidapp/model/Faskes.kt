package com.example.jalavidapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Faskes(

	@field:SerializedName("count_total")
	val countTotal: Int,

	@field:SerializedName("data")
	val data: List<DataItemFaskes>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DetailItem(

	@field:SerializedName("batal_vaksin")
	val batalVaksin: Int,

	@field:SerializedName("pending_vaksin_1")
	val pendingVaksin1: Int,

	@field:SerializedName("pending_vaksin_2")
	val pendingVaksin2: Int,

	@field:SerializedName("batch")
	val batch: String,

	@field:SerializedName("divaksin_1")
	val divaksin1: Int,

	@field:SerializedName("divaksin")
	val divaksin: Int,

	@field:SerializedName("divaksin_2")
	val divaksin2: Int,

	@field:SerializedName("kode")
	val kode: String,

	@field:SerializedName("pending_vaksin")
	val pendingVaksin: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("tanggal")
	val tanggal: String,

	@field:SerializedName("batal_vaksin_2")
	val batalVaksin2: Int,

	@field:SerializedName("batal_vaksin_1")
	val batalVaksin1: Int
)
@Parcelize
data class DataItemFaskes(

	@field:SerializedName("provinsi")
	val provinsi: String?= "Data Kosong",

	@field:SerializedName("kota")
	val kota: String?= "Data Kosong",

	@field:SerializedName("telp")
	val telp: String?= "Data Kosong",

	@field:SerializedName("source_data")
	val sourceData: String?= "Data Kosong",

	@field:SerializedName("latitude")
	val latitude: String?= "Data Kosong",

	@field:SerializedName("alamat")
	val alamat: String?= "Data Kosong",

	@field:SerializedName("nama")
	val nama: String?= "Data Kosong",

	@field:SerializedName("kode")
	val kode: String?= "Data Kosong",

	@field:SerializedName("kelas_rs")
	val kelasRs: String?= "Data Kosong" ,

	@field:SerializedName("jenis_faskes")
	val jenisFaskes: String?= "Data Kosong",

	@field:SerializedName("id")
	val id: Int? = 0,

	@field:SerializedName("longitude")
	val longitude: String? = "Data Kosong",

	@field:SerializedName("status")
	val status: String? = "Data Kosong"
) : Parcelable
