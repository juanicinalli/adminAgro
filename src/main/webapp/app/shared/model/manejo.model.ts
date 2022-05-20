import { IInsumos } from 'app/shared/model/insumos.model';

export interface IManejo {
  id?: number;
  labor?: string | null;
  mes?: string | null;
  costo?: number | null;
  insumos?: IInsumos[] | null;
}

export const defaultValue: Readonly<IManejo> = {};
