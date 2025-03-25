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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.michaelbentz.qriptid.R
import com.michaelbentz.qriptid.ui.component.ImageFromByteArray
import com.michaelbentz.qriptid.ui.component.Spacer
import com.michaelbentz.qriptid.ui.component.TopBar
import com.michaelbentz.qriptid.ui.state.QrCodeUiState
import com.michaelbentz.qriptid.viewmodel.QrCodeViewModel

@Composable
fun QrCodeScreen() {
    val focusManager = LocalFocusManager.current
    val viewModel: QrCodeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TopBar {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
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
                            .padding(16.dp),
                        singleLine = false,
                        minLines = 3,
                        maxLines = 6,
                        label = {
                            Text(text = stringResource(id = R.string.data_string))
                        },
                        value = dataString,
                        onValueChange = {
                            dataString = it
                        },
                    )
                    Spacer()
                    Button(
                        onClick = {
                            if (dataString.isNotEmpty()) {
                                focusManager.clearFocus()
                                viewModel.createQrCode(dataString.trim())
                            }
                        }
                    ) {
                        Text(text = stringResource(id = R.string.generate_qr_code))
                    }
                    Spacer()
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        stringResource(id = R.string.content_description_arrow_down),
                    )
                    Spacer()
                    when (val state = uiState) {
                        QrCodeUiState.Loading -> {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp),
                                textAlign = TextAlign.Center,
                                text = stringResource(R.string.generating),
                            )
                        }

                        is QrCodeUiState.Error -> {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp),
                                textAlign = TextAlign.Center,
                                text = state.message,
                            )
                        }

                        is QrCodeUiState.Data -> {
                            with(state.data) {
                                ImageFromByteArray(
                                    byteArray = bytes.toByteArray(),
                                    contentDescription = stringResource(
                                        id = R.string.content_description_qr_code
                                    )
                                )
                                Spacer()
                                Text(text = toString())
                                Spacer()
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}
