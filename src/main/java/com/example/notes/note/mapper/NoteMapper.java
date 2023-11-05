package com.example.notes.note.mapper;

import com.example.notes.note.dto.NoteDto;
import com.example.notes.note.dto.SendNoteDto;
import com.example.notes.note.dto.SendNoteFrom;
import com.example.notes.note.model.Note;
import com.example.notes.note.model.SendNote;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring")
public interface NoteMapper {
    Note toNote(NoteDto noteDto);

    SendNote toSendNote(SendNoteDto sendNoteDto);

    SendNoteFrom toSendNoteFrom(SendNote sendNote);
}