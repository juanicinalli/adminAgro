import { IManejo } from 'app/shared/model/manejo.model';
import { ILocalidad } from 'app/shared/model/localidad.model';

export interface ICampo {
  id?: number;
  numeroDeLote?: number | null;
  superficie?: number | null;
  tenencia?: string | null;
  cultivo?: string | null;
  manejo?: IManejo | null;
  localidad?: ILocalidad | null;
}

export const defaultValue: Readonly<ICampo> = {};
