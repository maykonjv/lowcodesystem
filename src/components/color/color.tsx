import {
  FormHelperText,
  FormControl,
  FormLabel,
  Input,
  InputGroup,
  InputLeftElement,
  Text,
  Spinner,
  Tooltip,
  GridItem,
} from '@chakra-ui/react';
import { FiInfo } from 'react-icons/fi';

export interface InputProps {
  id: string;
  label?: string;
  description?: string;
  tooltip?: string;
  value?: string;
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
  error?: string;
  isRequired?: boolean;
  hidden?: boolean;
  isDisabled?: boolean;
  isLoading?: boolean;
  ref?: any;
}

export const Color = (props: InputProps) => {
  const { id, label, description, value, onChange, error, hidden, isDisabled, isLoading, isRequired, tooltip, ref } =
    props;

  return (
    <GridItem key={`${id}_div`} id={`${id}_div`} colSpan={{ base: 1, md: 2, lg: 3 }}>
      <FormControl id={`${id}_formControl`} isRequired={isRequired} isInvalid={!!error}>
        {label ? (
          <FormLabel id={`${id}_label`} whiteSpace={'nowrap'}>
            {label}
            {tooltip && (
              <Tooltip label={tooltip} fontSize='md'>
                <FiInfo style={{ position: 'absolute', right: 0 }} />
              </Tooltip>
            )}
          </FormLabel>
        ) : (
          <></>
        )}
        <FormHelperText id={`${id}_helper`}>{description}</FormHelperText>
        <InputGroup mr={5} id={`${id}_input_group`}>
          <InputLeftElement
            id={`${id}_leftElement`}
            bg={isLoading ? '#b6b6b6' : value || '#000000'}
            borderLeftRadius={'md'}>
            <Spinner hidden={!isLoading} />
          </InputLeftElement>
          <Input
            id={`${id}_input`}
            bg='white'
            value={value || '#000000'}
            type='text'
            onChange={onChange}
            isDisabled={isDisabled}
            isRequired={isRequired}
            hidden={hidden}
            ref={ref}
          />
        </InputGroup>
        <Text style={{ color: 'red' }}>{error}</Text>
      </FormControl>
    </GridItem>
  );
};
