export interface PageType {
  id?: string;
  title?: string;
  actionNew?: ActionType;
  actionEdit?: ActionType;
  actionSearch?: ActionType;
  actionCustom?: ActionType;
}

type ActionType = {
  id: string;
  title: string;
  components: ComponentType[];
};

type ComponentType = {
  id: string;
  label: string;
  component: string;
  type: string;
  size: number;
  value: string;
  placeholder: string;
  options: string[];
};
