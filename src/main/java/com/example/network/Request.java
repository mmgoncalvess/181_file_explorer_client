package com.example.network;

public enum Request {
    ONGOING_DIRECTORY,
    PARENT,
    DIRECTORY,
    DELETE,
    NEW_DIRECTORY,
    RENAME,
    COPY,                          // Copy a file from remote host to the same remote host
    IMPORT,                        // Local host send a file to remote
    EXPORT                         // Remote host send a file to local
}
