package com.sms.stockmanagementsystem.project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class JSONConverter {




    public static void save(@NotNull ContainerItem item, @NotNull String server) throws IOException, ParseException {
        JSONObject file = getFile(server);
        JSONObject itemJson = new JSONObject();
        itemJson.put("id", item.getId());
        itemJson.put("time", item.getTime().toString());
        itemJson.put("user", item.getUser());
        itemJson.put("data", item.getData());
        file.put(item.getId(), itemJson);
        FileWriter fileWriter = new FileWriter(server + ".json");
        fileWriter.write(file.toJSONString());
        fileWriter.close();
    }

    public static @Nullable ContainerItem getContainerItems(@NotNull String server, @NotNull String id) throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) getFile(server).get(id);
        if (jsonObject == null) { return null; }
        return new ContainerItem(id, LocalDateTime.parse((String) jsonObject.get("time")), (String) jsonObject.get("user"), (String) jsonObject.get("data"));
    }

    private static JSONObject getFile(@NotNull String server) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        FileReader fileReader = new FileReader(server + ".json");
        jsonObject = (JSONObject) jsonParser.parse(fileReader);
        return jsonObject;
    }
}
