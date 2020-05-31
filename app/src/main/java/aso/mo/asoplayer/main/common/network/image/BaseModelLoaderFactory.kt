package aso.mo.asoplayer.main.common.network.image

import com.bumptech.glide.load.model.ModelLoaderFactory
import java.io.InputStream

abstract class BaseModelLoaderFactory<M> : ModelLoaderFactory<M, InputStream> {
    override fun teardown() {}
}