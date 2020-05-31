package aso.mo.asoplayer.main.common.network.image

import java.io.File

interface FileFetcher {
    fun fetchFile(): File
}