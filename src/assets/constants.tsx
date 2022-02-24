import {
  FaBook,
  FaChartPie,
  FaLightbulb,
  FaTicketAlt,
  FaUsers,
  FaUserTie,
} from "react-icons/fa";
import { PageType } from "./types";

export const menuData = [
  {
    title: "Overview",
    page: "overview",
    icon: <FaChartPie style={{ marginRight: "0.5rem" }} />,
  },
  {
    title: "Tickets",
    page: "tickets",
    icon: <FaTicketAlt style={{ marginRight: "0.5rem" }} />,
  },
  {
    title: "Ideas",
    page: "ideas",
    icon: <FaLightbulb style={{ marginRight: "0.5rem" }} />,
  },
  {
    title: "Contacts",
    page: "contacts",
    icon: <FaUsers style={{ marginRight: "0.5rem" }} />,
  },
  {
    title: "Agents",
    page: "agents",
    icon: <FaUserTie style={{ marginRight: "0.5rem" }} />,
  },
  {
    title: "Articles",
    page: "articles",
    icon: <FaBook style={{ marginRight: "0.5rem" }} />,
  },
];

export const tableTitles = [
  "Ticket details",
  "Customer name",
  "Date",
  "Priority",
];

export const data = [
  ["Contact Email not Linked", "Tom Cruise", "May 26, 2019", "High"],
  ["Adding Images to Featured Posts", "Matt Damon", "May 26, 2019", "low"],
  [
    "When will I be charged this month?",
    "Robert Downey",
    "May 26, 2019",
    "High",
  ],
  ["Payment not going through", "Christian Bale", "May 25, 2019", "Normal"],
  ["Unable to add replies", "Henry Cavil", "May 26, 2019", "High"],
];

export const pages = [
  {
    id: "overview",
    title: "Overview",
    actionNew: {
      id: "new",
      title: "New",
      components: [
        {
          id: "new-field-1",
          label: "Field 1",
          component: "Input",
          type: "text",
          size: 3,
          placeholder: "Enter ticket title",
          value: "",
          options: [],
        },
        {
          id: "new-field-2",
          label: "Field 2",
          component: "Input",
          type: "text",
          size: 3,
          placeholder: "Enter ticket title",
          value: "",
          options: [],
        },
      ],
    },
    actionEdid: {
      id: "edit",
      title: "Edit",
    },

    actionSearch: {
      id: "search",
      title: "Search",
    },
  },
  {
    id: "tickets",
    title: "Tickets",
  },
  {
    id: "ideas",
    title: "Ideas",
  },
  {
    id: "contacts",
    title: "Contacts",
  },
  {
    id: "agents",
    title: "Agents",
  },
  {
    id: "articles",
    title: "Articles",
  },
] as PageType[];
