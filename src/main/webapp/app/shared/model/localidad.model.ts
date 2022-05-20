import { ICampo } from 'app/shared/model/campo.model';
import { IProvincia } from 'app/shared/model/provincia.model';

export interface ILocalidad {
  id?: number;
  nombre?: string | null;
  direccion?: string | null;
  codigoPostal?: string | null;
  campos?: ICampo[] | null;
  provincia?: IProvincia | null;
}

export const defaultValue: Readonly<ILocalidad> = {};
