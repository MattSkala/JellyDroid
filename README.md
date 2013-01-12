JellyDroid
==========

The missing Android utilities library. It's a collection of the classes for faster Android apps development, especially simplifies working with async related tasks.

Features
--------
**AsyncImageView** - ImageView that can load remote images quickly and easily.

    AsyncImageView asyncImageView = findViewById(R.id.image);
    asyncImageView.loadImage("http://example.org/image.jpg");


**FileDownloader** - FileDownloader helps with downloading and storing remote files.

    FileDownloader fileDownloader = new FileDownloader();
    fileDownloader.download("http://example.org/file.zip", new FileDownloadListener() {
        @Override
        public void onSuccess(File file) {
            // Process the file...
        }
    };