package rtviwe.com.retabelo.geolocation

import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

class ConnectionListener : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    override fun onConnected(connectionHint: Bundle?) {
        Log.i("ConnectionListener", "API Client Connection Successful!")
    }

    override fun onConnectionSuspended(cause: Int) {
        Log.i("ConnectionListener", "API Client Connection Suspended!")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("ConnectionListener", "API Client Connection Failed!")
    }
}