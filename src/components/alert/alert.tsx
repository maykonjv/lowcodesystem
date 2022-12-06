/* eslint-disable react/prop-types */
import {
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
  Text,
  Button,
  Box,
} from '@chakra-ui/react';
import React from 'react';

export type AlertModalType = {
  title?: string;
  message: string;
  btn?: {
    text: string;
    onClick: () => void;
  };
  status?: 'success' | 'info' | 'warning' | 'error';
};

export function AlertModal({
  props,
  isOpen,
  onClose,
}: {
  props: AlertModalType;
  isOpen: boolean;
  onClose: () => void;
}) {
  const close = () => {
    onClose();
    props.btn = undefined;
  };

  return (
    <>
      <Modal closeOnOverlayClick={false} isOpen={isOpen} onClose={close}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>
            <Box hidden={props.status !== 'success'}>
              {/* <CheckCircleIcon color={'green.500'} mr={3} /> */}
              {props.title}
            </Box>
            <Box hidden={props.status !== 'info'}>
              {/* <InfoIcon color={'blue.500'} mr={3} /> */}
              {props.title}
            </Box>
            <Box hidden={props.status !== 'warning'}>
              {/* <WarningIcon color={'orange.500'} mr={3} /> */}
              {props.title}
            </Box>
            <Box hidden={props.status !== 'error'}>
              {/* <WarningIcon color={'red.500'} mr={3} /> */}
              {props.title}
            </Box>
          </ModalHeader>
          <ModalCloseButton onClick={close} />
          <ModalBody pb={6}>
            <Text dangerouslySetInnerHTML={{ __html: props.message }} />
          </ModalBody>

          <ModalFooter>
            <Button
              colorScheme='blue'
              mr={3}
              onClick={() => {
                props?.btn?.onClick();
                close();
              }}
              hidden={!props?.btn}>
              {props?.btn?.text}
            </Button>
            <Button onClick={close}>Fechar</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}
