import {
  FormControl,
  FormLabel,
  Tooltip,
  FormHelperText,
  Text,
  Spinner,
  Checkbox as ChakraCheckbox,
  GridItem,
} from '@chakra-ui/react';
import React from 'react';
import { FiInfo } from 'react-icons/fi';

interface CheckboxProps {
  id: string;
  label?: string;
  description?: string;
  tooltip?: string;
  value?: boolean;
  error?: string;
  isRequired?: boolean;
  hidden?: boolean;
  isDisabled?: boolean;
  ref?: any;
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
}
export const Checkbox = (props: CheckboxProps) => {
  const { id, label, description, tooltip, value, onChange, isRequired, isDisabled, hidden, error, ref } = props;
  return (
    <GridItem key={`${id}_div`} id={`${id}_div`} colSpan={{ base: 1, md: 2, lg: 3 }}>
      <FormControl id={`${id}_formControl`} isRequired={isRequired} isInvalid={!!error} hidden={hidden}>
        <br></br>
        <ChakraCheckbox isChecked={value} onChange={onChange} ref={ref} isDisabled={isDisabled} mt={2}>
          {label}
          {tooltip && (
            <Tooltip label={tooltip} fontSize='md'>
              <FiInfo style={{ position: 'absolute', right: 0 }} />
            </Tooltip>
          )}
          <FormHelperText id={`${id}_helper`}>{description}</FormHelperText>
        </ChakraCheckbox>
        <Text style={{ color: 'red' }}>{error}</Text>
      </FormControl>
    </GridItem>
  );
};
