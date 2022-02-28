import {
  Box,
  Button,
  DrawerBody,
  DrawerCloseButton,
  DrawerContent,
  DrawerFooter,
  DrawerHeader,
  Input
} from '@chakra-ui/react'
import { ChangeEvent, useContext, useState } from 'react'
import { DashboardContext } from '../../contexts/Dashboard.context'

export const MainContent = ({
  onOpen,
  onClose,
  title
}: {
  onOpen: () => void
  onClose: () => void
  title: string
}) => {
  const { page, setPage } = useContext(DashboardContext)
  const [form, setForm] = useState({ id: '' })

  const Add = (
    <Button
      variant="outline"
      onClick={() => {
        listInputs.push({
          name: form?.id || 'input ' + (listInputs.length + 1),
          value: ''
        })
        setPage(
          <Box>
            {listInputs.map(input => (
              <Input
                border="1px"
                h="12"
                m={2}
                p={2}
                borderColor="gray.200"
                borderRadius="md"
                key={input.name}
                name={input.name}
                placeholder={input.name}
                onFocus={onOpen}
              />
            ))}
          </Box>
        )
      }}
    >
      Add Field
    </Button>
  )
  const Add2 = (
    <Button
      variant="outline"
      onClick={() => {
        listInputs.push({
          name: form?.id || 'input ' + (listInputs.length + 1),
          value: ''
        })
        setPage(
          <Box>
            {listInputs.map(input => (
              <Box
                as="input"
                border="1px"
                h="12"
                m={2}
                p={2}
                borderColor="gray.200"
                borderRadius="md"
                placeholder={input.name}
                key={input.name}
                name={input.name}
                onFocus={onOpen}
              />
            ))}
          </Box>
        )
      }}
    >
      Add Field
    </Button>
  )

  return (
    <DrawerContent>
      <DrawerCloseButton />
      <DrawerHeader>{title}</DrawerHeader>

      <DrawerBody>
        {Add}
        {Add2}
        <Input
          placeholder="ID here..."
          onChange={(e: ChangeEvent<HTMLInputElement>) =>
            setForm({ id: e.target.value })
          }
        />
      </DrawerBody>

      <DrawerFooter>
        <Button variant="outline" mr={3} onClick={onClose}>
          Cancel
        </Button>
        <Button colorScheme="blue">Save</Button>
      </DrawerFooter>
    </DrawerContent>
  )
}

const listInputs: any[] = []
