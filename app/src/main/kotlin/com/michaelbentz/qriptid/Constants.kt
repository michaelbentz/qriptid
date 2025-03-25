package com.michaelbentz.qriptid

object Constants {
    const val TAG = "QRiptid"

    object Database {
        const val DATABASE_NAME = "qriptid_database"
    }

    object Network {
        const val BASE_URL_QR_SERVER = "https://api.qrserver.com/v1/"

        object Route {
            const val CREATE_QR_CODE = "create-qr-code/?size=750x750"
        }

        object Param {
            const val DATA = "data"
        }
    }

    object Ui {
        object Routes {
            const val QR_CODE_SCREEN = "QrCodeScreen"
        }
    }
}
