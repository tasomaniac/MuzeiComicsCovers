package com.tasomaniac.muzei.comiccovers;

import com.google.android.apps.muzei.api.provider.MuzeiArtProvider;

public class ComicVineArtProvider extends MuzeiArtProvider {

    @Override public void onLoadRequested(boolean initial) {
        ComicVineWorker.enqueue();
    }

}
