package com.example.notes.note.mapper;

import com.example.notes.note.dto.NoteDto;
import com.example.notes.note.dto.SendNoteDto;
import com.example.notes.note.model.Note;
import com.example.notes.note.model.SendNote;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface NoteMapper {
    NoteDto toNoteDto(Note note);
    Note toNote(NoteDto noteDto);
    SendNote toSendNote(SendNoteDto sendNoteDto);
    List<NoteDto> toNoteDtoList(List<Note> noteList);
}