import {
  FormControl,
  FormLabel,
  Tooltip,
  FormHelperText,
  Text,
  Spinner,
  Input as ChakraInput,
  GridItem,
} from '@chakra-ui/react';
import { FiInfo } from 'react-icons/fi';

interface InputProps {
  id: string;
  type: string;
  label?: string;
  description?: string;
  tooltip?: string;
  placeholder?: string;
  value?: string;
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
  error?: string;
  isRequired?: boolean;
  hidden?: boolean;
  isDisabled?: boolean;
  isLoading?: boolean;
  ref?: any;
}
export const Input = (props: InputProps) => {
  const {
    id,
    type = 'text',
    label,
    description,
    placeholder,
    tooltip,
    value,
    onChange,
    isRequired,
    isDisabled,
    isLoading,
    hidden,
    error,
    ref,
  } = props;
  return (
    <GridItem key={`${id}_div`} id={`${id}_div`} colSpan={{ base: 1, md: 2, lg: 3 }}>
      <FormControl id={`${id}_formControl`} isRequired={isRequired} isInvalid={!!error} hidden={hidden}>
        <FormLabel>
          {label}
          {tooltip && (
            <Tooltip label={tooltip} fontSize='md'>
              <FiInfo style={{ position: 'absolute', right: 0 }} />
            </Tooltip>
          )}
        </FormLabel>
        <FormHelperText id={`${id}_helper`}>{description}</FormHelperText>
        <ChakraInput
          id={`${id}_input`}
          name={id}
          bg='white'
          value={value}
          type={type}
          placeholder={placeholder}
          onChange={onChange}
          isDisabled={isDisabled}
          ref={ref}
        />
        <Spinner pos='absolute' right={2} bottom={2} hidden={!isLoading} />
        <Text style={{ color: 'red' }}>{error}</Text>
      </FormControl>
    </GridItem>
  );
};
