import { Box, Button, DrawerBody, DrawerCloseButton, DrawerContent, DrawerFooter, DrawerHeader, Input, Select, Wrap, WrapItem } from '@chakra-ui/react';
import { ChangeEvent, ReactEventHandler, useContext, useState } from 'react';
import { DashboardContext } from '../../contexts/Dashboard.context';

export const MainContent = ({ onOpen, onClose, title }: { onOpen: () => void; onClose: () => void; title: string }) => {
  const { page, setPage } = useContext(DashboardContext);
  const [form, setForm] = useState({ id: '' });

  const Add = (
    <Button
      variant="outline"
      mb={2}
      onClick={() => {
        listInputs.push({
          name: form?.id || 'input ' + (listInputs.length + 1),
          value: '',
        });
        setPage(
          <Wrap spacing={4}>
            {listInputs.map(input => (
              <WrapItem key={input.name}>
                <Input
                  border="1px"
                  h="12"
                  p={2}
                  bg={'white'}
                  borderColor="gray.200"
                  borderRadius="md"
                  key={input.name}
                  name={input.name}
                  placeholder={input.name}
                  onFocus={onOpen}
                />
              </WrapItem>
            ))}
          </Wrap>
        );
      }}
    >
      Add Field
    </Button>
  );

  return (
    <DrawerContent>
      <DrawerCloseButton />
      <DrawerHeader>{title}</DrawerHeader>

      <DrawerBody>
        {Add}
        <Select mb={2} placeholder="TYPE" h={'8'} onSelect={(e: any) => setForm({ id: e })}>
          <option value="1">Input</option>
          <option value="2">Area</option>
          <option value="3">Select</option>
        </Select>
        <Input mb={2} placeholder="ID" h={'8'} onChange={(e: ChangeEvent<HTMLInputElement>) => setForm({ id: e.target.value })} />
        <Input mb={2} placeholder="NAME" h={'8'} onChange={(e: ChangeEvent<HTMLInputElement>) => setForm({ id: e.target.value })} />
        <Input mb={2} placeholder="LABEL" h={'8'} onChange={(e: ChangeEvent<HTMLInputElement>) => setForm({ id: e.target.value })} />
      </DrawerBody>

      <DrawerFooter>
        <Button variant="outline" mr={3} onClick={onClose}>
          Cancel
        </Button>
        <Button colorScheme="blue">Save</Button>
      </DrawerFooter>
    </DrawerContent>
  );
};

const listInputs: any[] = [];
