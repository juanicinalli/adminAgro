import { ITarea } from 'app/shared/model/tarea.model';

export interface ITrabajo {
  id?: number;
  puesto?: string | null;
  salario?: number | null;
  tareas?: ITarea[] | null;
}

export const defaultValue: Readonly<ITrabajo> = {};
