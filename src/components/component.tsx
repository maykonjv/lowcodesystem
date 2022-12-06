import { ComponentSchemaType } from '../config/schema/schema';
import { Checkbox } from './checkbox/checkbox';
import { Color } from './color/color';
import { Input } from './input/input';

export interface ComponentProps {
  field: ComponentSchemaType;
  refs: any;
  data: any;
  setData: any;
}

export const Component = (props: ComponentProps) => {
  const { field, refs, data, setData } = props;
  switch (field.type) {
    case 'text':
      return (
        <Input
          id={field.id}
          error=''
          description={field.description}
          type={field.type}
          // ref={refs}
          value={data[field.id]}
          onChange={(e) => setData({ ...data, [field.id]: e.target.value })}
        />
      );
    case 'color':
      return (
        <Color
          id={field.id}
          error=''
          description={field.description}
          // ref={refs}
          value={data[field.id]}
          onChange={(e) => setData({ ...data, [field.id]: e.target.value })}
        />
      );
    case 'checkbox':
      return (
        <Checkbox
          id={field.id}
          error=''
          description={field.description}
          // ref={refs}
          value={data[field.id]}
          onChange={(e) => setData({ ...data, [field.id]: e.target.checked })}
        />
      );
    default:
      return (
        <Input
          id={field.id}
          error=''
          description={field.description}
          type={field.type}
          // ref={refs}
          value={data[field.id]}
          onChange={(e) => setData({ ...data, [field.id]: e.target.value })}
        />
      );
  }
};
