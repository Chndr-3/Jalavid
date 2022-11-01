package com.example.jalavidapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class RumahSakitItem(

	@field:SerializedName("tempat_tidur")
	val tempatTidur: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("lokasi")
	val lokasi: @RawValue LokasiRS? = null,

	@field:SerializedName("wilayah")
	val wilayah: String? = null,

	@field:SerializedName("kode_rs")
	val kodeRs: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("telepon")
	val telepon: String? = null,

	@field:SerializedName("tipe")
	val tipe: String? = null
): Parcelable
@Parcelize
data class LokasiRS(

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
) : Parcelable
