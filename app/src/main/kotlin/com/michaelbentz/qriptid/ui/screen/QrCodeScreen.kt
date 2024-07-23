package com.michaelbentz.qriptid.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.michaelbentz.qriptid.R
import com.michaelbentz.qriptid.network.NetworkState
import com.michaelbentz.qriptid.ui.component.ImageFromByteArray
import com.michaelbentz.qriptid.ui.component.Spacer
import com.michaelbentz.qriptid.ui.component.TopBar
import com.michaelbentz.qriptid.viewmodel.QrCodeViewModel

@Composable
fun QrCodeScreen() {
    val viewModel: QrCodeViewModel = hiltViewModel()
    val latestQrCodeState by viewModel.latestQrCodeStateFlow.collectAsState()
    val createQrCodeState by viewModel.createQrCodeStateFlow.collectAsState()
    val focusManager = LocalFocusManager.current
    TopBar {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    var dataString by remember {
                        mutableStateOf(String())
                    }
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        singleLine = false,
                        minLines = 3,
                        maxLines = 6,
                        label = {
                            Text(text = stringResource(id = R.string.data_string))
                        },
                        value = dataString,
                        onValueChange = {
                            dataString = it
                        }
                    )
                    Spacer()
                    Button(
                        onClick = {
                            if (dataString.isNotEmpty()) {
                                focusManager.clearFocus()
                                viewModel.createQrCode(dataString.trim())
                            }
                        }) {
                        Text(text = stringResource(id = R.string.generate_qr_code))
                    }
                    Spacer()
                    when (val networkState = createQrCodeState) {
                        NetworkState.Idle -> {
                            NetworkStateText(String())
                        }

                        is NetworkState.Loading -> {
                            NetworkStateText(stringResource(id = R.string.generating))
                        }

                        is NetworkState.Success -> {
                            NetworkStateText(stringResource(id = R.string.success))
                        }

                        is NetworkState.Error -> {
                            NetworkStateText("${networkState.message}")
                        }
                    }
                    Spacer()
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        stringResource(id = R.string.content_description_arrow_down),
                    )
                    Spacer()
                    latestQrCodeState?.let { qrCodeEntity ->
                        ImageFromByteArray(
                            byteArray = qrCodeEntity.bytes,
                            contentDescription = stringResource(
                                id = R.string.content_description_qr_code
                            )
                        )
                        Spacer()
                        Text(text = qrCodeEntity.toString())
                        Spacer()
                    }
                }
            }
        }
    }
}

@Composable
private fun NetworkStateText(text: String) {
    Text(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
        textAlign = TextAlign.Center,
        text = text
    )
}