import { ITrabajo } from 'app/shared/model/trabajo.model';

export interface IEmpleado {
  id?: number;
  nombre?: string | null;
  apellido?: string | null;
  email?: string | null;
  numeroDeTelefono?: string | null;
  salario?: number | null;
  trabajo?: ITrabajo | null;
}

export const defaultValue: Readonly<IEmpleado> = {};
