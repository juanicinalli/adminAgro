import { ITrabajo } from 'app/shared/model/trabajo.model';

export interface ITarea {
  id?: number;
  nombre?: string | null;
  descripcion?: string | null;
  trabajo?: ITrabajo | null;
}

export const defaultValue: Readonly<ITarea> = {};
