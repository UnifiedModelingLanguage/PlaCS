package pkg_placs;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlaCS_Window{
    private JFrame frame;
    private String[] label_strings={"Username","Password","set seed","set seed","enable special","set seed","set filename","set size","Original text","Scrambled text","enable special", "length", "Roll dice", "d6", "d10","d25","d100","d1000"};
    private JLabel[] labels=new JLabel[label_strings.length];
    private int btn_size=40;
    private int img_size=256;
    private String asset_prefix="res/assets/";
    private String asset_suffix=".png";
    private String[] button_image_strings={"printer","dice","dice","dice","arrow","dice","dice","dice","dice","dice"};
    private Image[] button_images=new Image[button_image_strings.length];
    private JButton[] buttons=new JButton[button_images.length];
    private String[] text_strings={"","","0","0","","0","16",""};
    private JTextField[] texts=new JTextField[text_strings.length];
    private JCheckBox[] checks=new JCheckBox[6];
    private JTextArea[] areas=new JTextArea[2];
    private JScrollPane[] scrollable_area=new JScrollPane[2];

    private BufferedImage image;
    private JLabel profile_pic;
    private String username;
    private int username_pos_in_text_strings=0;
    private String password;
    private int password_pos_in_text_strings=1;


    public PlaCS_Window(){
        frame=new JFrame("PlaCS");
        frame.setSize(1000,800);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

        init_components();
        event_handling();
        layout();
        frame.setVisible(true);
    }

    private void init_components(){
        username=PlaCS_Logic.generate_username(false);
        password=PlaCS_Logic.generate_password(16,false);
        text_strings[username_pos_in_text_strings]=username;
        text_strings[password_pos_in_text_strings]=password;
        image=PlaCS_Logic.make_color_image(img_size);
        ImageIcon icon = new ImageIcon(image);
        profile_pic=new JLabel("");
        profile_pic.setIcon(icon);

        for(int i=0;i<label_strings.length;i++){
            labels[i]=new JLabel(label_strings[i]);
        }
        for(int i=0;i<texts.length;i++){
            texts[i]=new JTextField(text_strings[i]);
        }
        for(int i=0;i<checks.length;i++){
            checks[i]=new JCheckBox();
        }
        for(int i=0;i<buttons.length;i++){

            button_images[i] = null;
            try {
                button_images[i] = ImageIO.read(new File(asset_prefix+button_image_strings[i]+asset_suffix));
            } catch (IOException e) {
                System.out.println(e);
            }
            button_images[i] = button_images[i].getScaledInstance( btn_size, btn_size,  java.awt.Image.SCALE_SMOOTH ) ;
            buttons[i] = new JButton(new ImageIcon(button_images[i]));
            buttons[i].setBorder(BorderFactory.createEmptyBorder());
            buttons[i].setContentAreaFilled(false);
            //buttons[i].setBounds(btn_size, btn_size);
        }
        areas[0]=new JTextArea("I am a potato.", 40,40);
        areas[1]=new JTextArea("Iodine americium adenine potato?", 40,40);
        areas[1].setEditable(false);
        scrollable_area[0] = new JScrollPane(areas[0]);
        scrollable_area[1] = new JScrollPane(areas[1]);
    }

    private void roll_dice(int nb_face){
        int val=PlaCS_Logic.roll_dice(nb_face);
        text_strings[7]=""+val;
        texts[7].setText(text_strings[7]);
    }

    private void event_handling(){
        buttons[0].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checks[1].isSelected())
                    PlaCS_Logic.print(image,"jpg",texts[4].getText());
                else
                    PlaCS_Logic.print(image);
            }
        });
        buttons[1].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checks[0].isSelected()){
                    if (texts[5].getText().matches("-?\\d+"))
                        image=PlaCS_Logic.make_color_image(Integer.parseInt(texts[5].getText()),img_size,img_size);
                    else
                        image=PlaCS_Logic.make_faulty_color_image(img_size,img_size);
                }
                else{
                    image=PlaCS_Logic.make_color_image(img_size);
                }
                ImageIcon icon = new ImageIcon(image);
                profile_pic.setIcon(icon);

            }
        });
        buttons[2].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checks[2].isSelected()) {
                    if (texts[2].getText().matches("-?\\d+"))
                        username = PlaCS_Logic.generate_username(Integer.parseInt(texts[2].getText()), checks[3].isSelected());
                    else
                        username="?";
                }
                else{
                    username=PlaCS_Logic.generate_username(checks[3].isSelected());
                }
                text_strings[username_pos_in_text_strings]=username;
                texts[0].setText(username);
            }
        });
        buttons[3].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(texts[6].getText().matches("-?\\d+")){
                    if(checks[4].isSelected()) {
                        if (texts[3].getText().matches("-?\\d+"))
                            password = PlaCS_Logic.generate_password(Integer.parseInt(texts[3].getText()),Integer.parseInt(texts[6].getText()), checks[5].isSelected());
                        else
                            password="?";
                    }
                    else{
                        password=PlaCS_Logic.generate_password(Integer.parseInt(texts[6].getText()),checks[5].isSelected());
                    }
                }
                else{
                    password="?";
                }
                text_strings[password_pos_in_text_strings]=password;
                texts[1].setText(password);
            }
        });
        buttons[4].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String raw_scrambled_message="";
                String scrambled_message="";
                if(areas[0].getText().length()>0)
                    raw_scrambled_message=PlaCS_Logic.generate_scrambled_message(areas[0].getText());
                else
                    raw_scrambled_message=PlaCS_Logic.generate_scrambled_generic_message();

                int line_length=40;
                int cur_length=0;
                String[] parse=raw_scrambled_message.split(" ");
                for(int i=0;i<parse.length;i++){
                    if(cur_length+parse[i].length()+1<line_length){
                        scrambled_message+=parse[i]+' ';
                    }
                    else{
                        scrambled_message+='\n'+parse[i]+' ';
                        cur_length=0;
                    }
                    cur_length+=parse[i].length()+1;
                }
                areas[1].setText(scrambled_message);

            }
        });
        buttons[5].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                roll_dice(6);
            }
        });
        buttons[6].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                roll_dice(10);
            }
        });
        buttons[7].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                roll_dice(25);
            }
        });
        buttons[8].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                roll_dice(100);
            }
        });
        buttons[9].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                roll_dice(1000);
            }
        });
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        frame.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
    private void layout(){

        frame.setLayout(new GridLayout(2,1));

        JPanel info_section=new JPanel();
        info_section.setLayout(new BorderLayout());

        JPanel image_panel=new JPanel();
        image_panel.setLayout(new BorderLayout());
        image_panel.add(profile_pic, BorderLayout.CENTER);

        JPanel image_buttons_panel=new JPanel(new GridLayout(1,4));
        image_buttons_panel.add(new JLabel(""));
        image_buttons_panel.add(buttons[1]);
        image_buttons_panel.add(buttons[0]);
        image_buttons_panel.add(new JLabel(""));
        image_panel.add(image_buttons_panel,BorderLayout.SOUTH);

        info_section.add(image_panel, BorderLayout.WEST);

        JPanel info_panel=new JPanel();
        info_panel.setLayout(new GridLayout(4,1));


        JPanel dice_panel=new JPanel(new GridLayout(1,12));
        dice_panel.add(labels[12]);
        dice_panel.add(texts[7]);
        dice_panel.add(buttons[5]);
        dice_panel.add(labels[13]);
        dice_panel.add(buttons[6]);
        dice_panel.add(labels[14]);
        dice_panel.add(buttons[7]);
        dice_panel.add(labels[15]);
        dice_panel.add(buttons[8]);
        dice_panel.add(labels[16]);
        dice_panel.add(buttons[9]);
        dice_panel.add(labels[17]);

        info_panel.add(dice_panel);

        JPanel user_row=new JPanel(new GridLayout(1,6));
        user_row.add(labels[0]);
        JPanel centered_user_text_panel=new JPanel(new GridLayout(3,1));
        centered_user_text_panel.add(new JLabel(""));
        centered_user_text_panel.add(texts[0]);
        centered_user_text_panel.add(new JLabel(""));
        user_row.add(centered_user_text_panel);
        user_row.add(buttons[2]);
        JPanel user_check_row=new JPanel(new GridLayout(3,1));
        user_check_row.add(new JLabel(""));
        user_check_row.add(checks[2]);
        user_check_row.add(checks[3]);
        user_row.add(user_check_row);
        JPanel user_label_check_row=new JPanel(new GridLayout(3,1));
        user_label_check_row.add(new JLabel(""));
        user_label_check_row.add(labels[2]);
        user_label_check_row.add(labels[10]);
        user_row.add(user_label_check_row);
        JPanel user_text_check_row=new JPanel(new GridLayout(3,1));
        user_text_check_row.add(new JLabel(""));
        user_text_check_row.add(texts[2]);
        user_text_check_row.add(new JLabel(""));
        user_row.add(user_text_check_row);
        info_panel.add(user_row);

        JPanel password_row=new JPanel(new GridLayout(1,6));
        password_row.add(labels[1]);
        JPanel centered_password_text_panel=new JPanel(new GridLayout(3,1));
        centered_password_text_panel.add(new JLabel(""));
        centered_password_text_panel.add(texts[1]);
        centered_password_text_panel.add(new JLabel(""));
        password_row.add(centered_password_text_panel);
        password_row.add(buttons[3]);
        JPanel password_check_row=new JPanel(new GridLayout(3,1));
        password_check_row.add(new JLabel(""));
        password_check_row.add(checks[4]);
        password_check_row.add(checks[5]);
        password_row.add(password_check_row);
        JPanel password_label_check_row=new JPanel(new GridLayout(3,1));
        password_label_check_row.add(labels[11]);
        password_label_check_row.add(labels[3]);
        password_label_check_row.add(labels[4]);
        password_row.add(password_label_check_row);
        JPanel password_text_check_row=new JPanel(new GridLayout(3,1));
        password_text_check_row.add(texts[6]);
        password_text_check_row.add(texts[3]);
        password_text_check_row.add(new JLabel(""));
        password_row.add(password_text_check_row);
        info_panel.add(password_row);


        JPanel img_all_row=new JPanel(new GridLayout(3,1));
        JPanel img_seed_row=new JPanel(new GridLayout(1,6));
        img_seed_row.add(checks[0]);
        img_seed_row.add(labels[5]);
        img_seed_row.add(texts[5]);
        img_seed_row.add(new JLabel(""));
        img_seed_row.add(new JLabel(""));
        img_seed_row.add(new JLabel(""));
        img_all_row.add(img_seed_row);


        JPanel img_path_row=new JPanel(new GridLayout(1,6));
        img_path_row.add(checks[1]);
        img_path_row.add(labels[6]);
        img_path_row.add(texts[4]);
        img_path_row.add(new JLabel(""));
        img_path_row.add(new JLabel(""));
        img_path_row.add(new JLabel(""));
        img_all_row.add(img_path_row);
        img_all_row.add(new JLabel(""));
        info_panel.add(img_all_row);

        info_section.add(info_panel, BorderLayout.CENTER);
        frame.add(info_section);


        JPanel area_section=new JPanel(new BorderLayout());
        JPanel area_north_section=new JPanel(new GridLayout(1,3));
        area_north_section.add(labels[8]);
        area_north_section.add(new JLabel(""));
        area_north_section.add(labels[9]);
        area_section.add(area_north_section,BorderLayout.NORTH);


        JPanel area_center_section=new JPanel(new GridLayout(1,3));
        JPanel arrow_panel=new JPanel(new GridLayout(3,1));
        arrow_panel.add(new JLabel(""));
        arrow_panel.add(buttons[4]);
        arrow_panel.add(new JLabel(""));
        area_center_section.add(scrollable_area[0]);
        area_center_section.add(arrow_panel);
        area_center_section.add(scrollable_area[1]);
        area_section.add(area_center_section, BorderLayout.CENTER);

        frame.add(area_section);

        Color pale_cyan=new Color(200,255,255);
        Color pale_blue=new Color(230,230,255);
        Color pale_green=new Color(230,255,230);
        Color pale_red=new Color(255,230,230);
        Color pale_orange=new Color(255,230,200);


        dice_panel.setBackground(pale_orange);

        image_buttons_panel.setBackground(pale_cyan);
        image_panel.setBackground(pale_cyan);
        info_panel.setBackground(pale_cyan);
        img_all_row.setBackground(pale_cyan);
        img_seed_row.setBackground(pale_cyan);
        checks[0].setBackground(pale_cyan);
        img_path_row.setBackground(pale_cyan);
        checks[1].setBackground(pale_cyan);

        user_row.setBackground(pale_blue);
        centered_user_text_panel.setBackground(pale_blue);
        user_check_row.setBackground(pale_blue);
        checks[2].setBackground(pale_blue);
        checks[3].setBackground(pale_blue);
        user_label_check_row.setBackground(pale_blue);
        user_text_check_row.setBackground(pale_blue);

        password_row.setBackground(pale_green);
        centered_password_text_panel.setBackground(pale_green);
        password_check_row.setBackground(pale_green);
        checks[4].setBackground(pale_green);
        checks[5].setBackground(pale_green);
        password_label_check_row.setBackground(pale_green);
        password_text_check_row.setBackground(pale_green);

        area_north_section.setBackground(pale_red);
        arrow_panel.setBackground(pale_red);
    }


}
