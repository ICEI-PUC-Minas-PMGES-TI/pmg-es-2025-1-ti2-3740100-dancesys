EXEC sp_rename 'Aula_Experimetal', 'Aula_Experimental';
EXEC sp_rename 'Aula_Experimental.dataHorarioInico', 'data_horario_inicio', 'COLUMN';
EXEC sp_rename 'Aula_Experimental.dataHorarioFim', 'data_horario_fim', 'COLUMN';
