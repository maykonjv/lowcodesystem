import { TableColumnType } from '../../config/schema/schema';
import { Table, Thead, Tbody, Tr, Th, Td, chakra, Box, Flex, Text, IconButton, Tfoot } from '@chakra-ui/react';
import {
  useReactTable,
  flexRender,
  getCoreRowModel,
  ColumnDef,
  SortingState,
  getSortedRowModel,
  getPaginationRowModel,
  Cell,
  ColumnDefBase,
} from '@tanstack/react-table';
import * as React from 'react';
import { FaSort, FaSortDown, FaSortUp } from 'react-icons/fa';
import {
  FiChevronLeft,
  FiChevronRight,
  FiChevronsLeft,
  FiChevronsRight,
  FiEdit,
  FiSearch,
  FiTrash2,
} from 'react-icons/fi';

export type DataTableProps<Data extends object> = {
  data: Data[];
  columns: ColumnDef<Data, any>[];
  columnsSchema: TableColumnType[] | undefined;
  actions?: Array<'create' | 'update' | 'delete' | 'search' | 'view' | 'custom'>;
  handleView?: (key: string) => void;
  handleEdit?: (key: string) => void;
  handleDelete?: (key: string) => void;
};

export function DataTable<Data extends object>({
  data,
  columns,
  columnsSchema,
  actions,
  handleDelete,
  handleEdit,
  handleView,
}: DataTableProps<Data>) {
  const [sorting, setSorting] = React.useState<SortingState>([]);
  const table = useReactTable({
    columns,
    data,
    getCoreRowModel: getCoreRowModel(),
    onSortingChange: setSorting,
    getSortedRowModel: getSortedRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    state: {
      sorting,
    },
  });

  return (
    <Box bg={'white'} borderRadius={10}>
      <Table>
        <Thead>
          {table.getHeaderGroups().map((headerGroup) => (
            <Tr key={headerGroup.id}>
              {headerGroup.headers
                .filter((h) => h.column.id !== '_key_')
                .map((header) => {
                  // see https://tanstack.com/table/v8/docs/api/core/column-def#meta to type this correctly
                  const meta: any = header.column.columnDef.meta;
                  const columnSchema = columnsSchema?.find((c) => c.id === header.column.id);
                  if (header.column.id === '_actions_') {
                    return (
                      <Th w={columnSchema?.width} key={header.id} hidden={!data || data.length == 0}>
                        {flexRender(header.column.columnDef.header, header.getContext())}
                      </Th>
                    );
                  }
                  return (
                    <Th
                      w={columnSchema?.width}
                      key={header.id}
                      onClick={header.column.getToggleSortingHandler()}
                      isNumeric={meta?.isNumeric}
                      cursor='pointer'>
                      <Flex alignItems='center' justifyItems={'start'}>
                        {flexRender(header.column.columnDef.header, header.getContext())}
                        <chakra.span pl='4' hidden={!data || data.length == 0}>
                          {header.column.getIsSorted() ? (
                            header.column.getIsSorted() === 'desc' ? (
                              <FaSortDown aria-label='sorted descending' />
                            ) : (
                              <FaSortUp aria-label='sorted ascending' />
                            )
                          ) : null}
                        </chakra.span>
                      </Flex>
                    </Th>
                  );
                })}
            </Tr>
          ))}
        </Thead>
        <Tbody>
          {table.getRowModel().rows.map((row) => (
            <Tr key={row.id}>
              {row
                .getVisibleCells()
                .filter((c) => c.column.id !== '_key_')
                .map((cell) => {
                  // see https://tanstack.com/table/v8/docs/api/core/column-def#meta to type this correctly
                  const meta: any = cell.column.columnDef.meta;
                  if (cell.column.id === '_actions_') {
                    return (
                      <Td p={1} key={cell.id}>
                        <IconButton
                          onClick={() => handleView && handleView(row.getValue('_key_'))}
                          aria-label='search-action'
                          title='Visualizar'
                          icon={<FiSearch />}
                          size='sm'
                          mr={2}
                          hidden={!actions?.includes('search')}
                        />
                        <IconButton
                          onClick={() => handleEdit && handleEdit(row.getValue('_key_'))}
                          aria-label='edit-action'
                          title='Alterar'
                          icon={<FiEdit />}
                          size='sm'
                          mr={2}
                          hidden={!actions?.includes('update')}
                        />
                        <IconButton
                          onClick={() => {
                            handleDelete && handleDelete(row.getValue('_key_'));
                          }}
                          aria-label='delete-action'
                          title='Excluir'
                          icon={<FiTrash2 />}
                          size='sm'
                          mr={2}
                          hidden={!actions?.includes('delete')}
                        />
                      </Td>
                    );
                  }
                  return (
                    <Td key={cell.id} isNumeric={meta?.isNumeric}>
                      {flexRender(cell.column.columnDef.cell, cell.getContext())}
                    </Td>
                  );
                })}
            </Tr>
          ))}
        </Tbody>
        <Tfoot hidden={data && data.length > 0}>
          <Tr>
            <Th colSpan={table.getAllColumns().length}>
              <Flex justifyContent='center' alignItems='center' m={10}>
                <Text>Nenhum registro encontrado</Text>
              </Flex>
            </Th>
          </Tr>
        </Tfoot>
      </Table>
      <Flex direction={'row'} p={2} hidden={!data || data.length == 0}>
        <Flex alignItems={'center'}>
          <Text>
            {table.getRowModel().rows.length}/{data.length}
          </Text>
        </Flex>
        <Flex
          alignItems={'center'}
          flex={1}
          justifyContent='center'
          hidden={table.getCanNextPage() || table.getCanPreviousPage()}></Flex>
        <Flex
          alignItems={'center'}
          flex={1}
          justifyContent='center'
          hidden={!table.getCanNextPage() && !table.getCanPreviousPage()}>
          <IconButton
            aria-label='left-page'
            onClick={() => table.setPageIndex(0)}
            disabled={!table.getCanPreviousPage()}
            icon={<FiChevronsLeft />}
          />
          <IconButton
            aria-label='left-page'
            onClick={() => table.previousPage()}
            disabled={!table.getCanPreviousPage()}
            icon={<FiChevronLeft />}
          />
          <Flex mx={5} alignItems={'center'}>
            <Text fontWeight={'bold'}>
              {table.getState().pagination.pageIndex + 1}/{table.getPageCount()}
            </Text>
          </Flex>
          <IconButton
            aria-label='right-page'
            onClick={() => table.nextPage()}
            disabled={!table.getCanNextPage()}
            icon={<FiChevronRight />}
          />
          <IconButton
            aria-label='right-page'
            onClick={() => table.setPageIndex(table.getPageCount() - 1)}
            disabled={!table.getCanNextPage()}
            icon={<FiChevronsRight />}
          />
        </Flex>
        <Flex alignItems={'center'}>
          <select
            value={table.getState().pagination.pageSize}
            onChange={(e) => {
              table.setPageSize(Number(e.target.value));
            }}>
            {[10, 25, 50, 100].map((pageSize) => (
              <option key={pageSize} value={pageSize}>
                {pageSize} por página
              </option>
            ))}
          </select>
        </Flex>
      </Flex>
    </Box>
  );
}
