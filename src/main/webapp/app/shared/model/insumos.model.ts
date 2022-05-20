import { IManejo } from 'app/shared/model/manejo.model';

export interface IInsumos {
  id?: number;
  categoria?: string | null;
  nombre?: string | null;
  precioPorUnidad?: number | null;
  unidad?: string | null;
  manejo?: IManejo | null;
}

export const defaultValue: Readonly<IInsumos> = {};
