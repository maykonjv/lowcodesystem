import {
  Table as TableChakra,
  Thead,
  Tbody,
  Tfoot,
  Tr,
  Th,
  Td,
  TableCaption,
  TableContainer,
  Button,
  Box,
} from '@chakra-ui/react';

export interface TableProps {
  data: any[];
  columns: { label: string; key: string }[];
  actions?: any[];
  isLoading?: boolean;
  onEdit?: (id: string) => void;
  onDelete?: (id: string) => void;
  onAction?: (action: any, id: string) => void;
}

export const Table = (props: TableProps) => {
  const { data, columns, actions, isLoading, onEdit, onDelete, onAction } = props;

  return (
    <Box bg={'white'} borderRadius={10}>
      <TableContainer>
        <TableChakra variant='simple'>
          <Thead>
            <Tr>
              {columns.map((column, index) => (
                <Th key={index}>{column.label}</Th>
              ))}
              {actions && <Th>Ações</Th>}
            </Tr>
          </Thead>
          <Tbody>
            {data.map((row, index) => (
              <Tr key={index}>
                {columns.map((column, index) => (
                  <Td key={index}>{String(row[column.key])}</Td>
                ))}
                {actions && (
                  <Td>
                    {actions.map((action, index) => (
                      <Button
                        key={index}
                        colorScheme='brand2'
                        size='xs'
                        onClick={() => onAction && onAction(action, row.id)}>
                        {action.label}
                      </Button>
                    ))}
                  </Td>
                )}
              </Tr>
            ))}
          </Tbody>
        </TableChakra>
      </TableContainer>
    </Box>
  );
};
