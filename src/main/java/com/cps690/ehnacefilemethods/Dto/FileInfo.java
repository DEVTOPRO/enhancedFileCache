package com.cps690.ehnacefilemethods.Dto;

import java.nio.file.attribute.FileTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileInfo {
private String fileName;
private long creationTimeInMin;
private long fileSize;
}
