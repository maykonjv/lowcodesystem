import React from 'react';
import {Button, Drawer, DrawerOverlay, useDisclosure} from '@chakra-ui/react';
import {FiSettings} from 'react-icons/fi';
import {MainContent} from './main.dev';

export function Sidepanel() {
  const {isOpen, onOpen, onClose} = useDisclosure();
  const btnRef = React.useRef();

  return (
    <>
      <Button position={'absolute'} right={5} bottom={20} ref={btnRef} colorScheme="teal" borderRadius={'50%'} h={'14'} w={'14'} onClick={onOpen}>
        <FiSettings size={'1.5rem'} />
      </Button>
      <Drawer isOpen={isOpen} placement="right" onClose={onClose} finalFocusRef={btnRef}>
        {/* <DrawerOverlay /> */}
        <MainContent title="Main" onClose={onClose} onOpen={onOpen} />
      </Drawer>
    </>
  );
}
