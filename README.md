JellyDroid
==========

The missing Android utilities library. It's a collection of the classes for faster Android apps development, especially simplifies working with async related tasks.

Features
--------
**AsyncImageHelper** - Helper that makes loading remote images quick and easy.

    AsyncImageHelper asyncImageHelper = new AsyncImageHelper();
    asyncImageHelper.loadImage(findViewById(R.id.imageView), "http://example.org/image.jpg");


**FileDownloader** - FileDownloader helps with downloading and storing remote files.

    FileDownloader fileDownloader = new FileDownloader();
    fileDownloader.download("http://example.org/file.zip", new FileDownloadListener() {
        @Override
        public void onSuccess(File file) {
            // Process the file...
        }
    };