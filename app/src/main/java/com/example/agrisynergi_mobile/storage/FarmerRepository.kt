package com.example.agrisynergi_mobile.storage

class FarmerRepository(private val farmerDao: FarmerDao) {

    suspend fun insertFarmer(farmer: FarmerEntity) {
        farmerDao.insertFarmer(farmer)
    }

    suspend fun getAllFarmers(): List<FarmerEntity> {
        return farmerDao.getAllFarmers()
    }

    suspend fun getFarmerById(id: Int): FarmerEntity {
        return farmerDao.getFarmerById(id)
    }

    suspend fun updateFarmer(farmer: FarmerEntity) {
        farmerDao.updateFarmer(farmer)
    }

    suspend fun deleteFarmer(farmer: FarmerEntity) {
        farmerDao.deleteFarmer(farmer)
    }
}
