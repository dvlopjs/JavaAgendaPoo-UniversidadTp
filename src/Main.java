import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static Agenda agenda = new Agenda();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Agenda de Contactos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLayout(new BorderLayout());
            frame.setBackground(Color.WHITE);

            // Panel para mostrar contactos
            DefaultListModel<String> listModel = new DefaultListModel<>();
            JList<String> contactoList = new JList<>(listModel);
            contactoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            contactoList.setBackground(new Color(240, 240, 240));
            contactoList.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            frame.add(new JScrollPane(contactoList), BorderLayout.CENTER);

            // Panel para agregar contactos
            JPanel addPanel = new JPanel();
            addPanel.setLayout(new GridBagLayout());
            addPanel.setBorder(BorderFactory.createTitledBorder("Agregar Contacto"));
            addPanel.setBackground(new Color(255, 255, 255));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;

            JTextField nameField = new JTextField(15);
            JTextField phoneField = new JTextField(15);
            JTextField emailField = new JTextField(15);
            JButton addButton = new JButton("Agregar");

            gbc.gridx = 0;
            gbc.gridy = 0;
            addPanel.add(new JLabel("Nombre:"), gbc);
            gbc.gridx = 1;
            addPanel.add(nameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            addPanel.add(new JLabel("Teléfono:"), gbc);
            gbc.gridx = 1;
            addPanel.add(phoneField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            addPanel.add(new JLabel("Email:"), gbc);
            gbc.gridx = 1;
            addPanel.add(emailField, gbc);

            gbc.gridx = 1;
            gbc.gridy = 3;
            addPanel.add(addButton, gbc);

            frame.add(addPanel, BorderLayout.NORTH);

            // Panel para buscar y eliminar contactos
            JPanel searchPanel = new JPanel();
            searchPanel.setLayout(new FlowLayout());
            searchPanel.setBorder(BorderFactory.createTitledBorder("Buscar/Eliminar Contacto"));

            JTextField searchField = new JTextField(15);
            JButton searchButton = new JButton("Buscar");
            JButton deleteButton = new JButton("Eliminar");

            searchPanel.add(new JLabel("Nombre:"));
            searchPanel.add(searchField);
            searchPanel.add(searchButton);
            searchPanel.add(deleteButton);

            frame.add(searchPanel, BorderLayout.SOUTH);

            // Acción del botón Agregar
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nombre = nameField.getText();
                    String telefono = phoneField.getText();
                    String email = emailField.getText();
                    if (!nombre.isEmpty() && !telefono.isEmpty() && !email.isEmpty()) {
                        Contacto contacto = new Contacto(nombre, telefono, email);
                        agenda.agregarContacto(contacto);
                        listModel.addElement(contacto.toString());
                        nameField.setText("");
                        phoneField.setText("");
                        emailField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Acción del botón Buscar
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nombre = searchField.getText();
                    Contacto contacto = agenda.buscarContacto(nombre);
                    if (contacto != null) {
                        JOptionPane.showMessageDialog(frame, "Contacto encontrado: " + contacto);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Contacto no encontrado.");
                    }
                }
            });

            // Acción del botón Eliminar
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nombre = searchField.getText();
                    agenda.eliminarContacto(nombre);
                    listModel.removeAllElements();
                    agenda.listarContactos().forEach(c -> listModel.addElement(c.toString()));
                    searchField.setText("");

                    if(nombre.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Por favor ingrese el nombre del contacto que desea eliminar.");
                    }else {
                        JOptionPane.showMessageDialog(frame, "Contacto eliminado.");
                    }

                }
            });

            frame.setVisible(true);
        });
    }
}